/**
 * 自定义的行解析器，参考LineDeserializer，请注意代码改造部分
 */
@InterfaceAudience.Private
@InterfaceStability.Evolving
public class CustomizeLineDeserializer implements EventDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(CustomizeLineDeserializer.class);

    private final ResettableInputStream in;
    private final Charset outputCharset;
    private final int maxLineLength;
    private volatile boolean isOpen;

    public static final String OUT_CHARSET_KEY = "outputCharset";
    public static final String CHARSET_DFLT = "UTF-8";

    public static final String MAXLINE_KEY = "maxLineLength";
    public static final int MAXLINE_DFLT = 2048;

    CustomizeLineDeserializer(Context context, ResettableInputStream in) {
        this.in = in;
        this.outputCharset = Charset.forName(
                context.getString(OUT_CHARSET_KEY, CHARSET_DFLT));
        this.maxLineLength = context.getInteger(MAXLINE_KEY, MAXLINE_DFLT);
        this.isOpen = true;
    }

    /**
     * Reads a line from a file and returns an event
     *
     * @return Event containing parsed line
     * @throws IOException
     */
    @Override
    public Event readEvent() throws IOException {
        ensureOpen();
        String line = readLine();
        if (line == null) {
            return null;
        } else {
            return EventBuilder.withBody(line, outputCharset);
        }
    }

    /**
     * Batch line read
     *
     * @param numEvents Maximum number of events to return.
     * @return List of events containing read lines
     * @throws IOException
     */
    @Override
    public List<Event> readEvents(int numEvents) throws IOException {
        ensureOpen();
        List<Event> events = Lists.newLinkedList();

        //Optional<FileInfo> currentFile = Optional.absent();
        //String filename = currentFile.get().getFile().getAbsolutePath();

        for (int i = 0; i < numEvents; i++) {
            Event event = readEvent();
            /*源码
            if (event != null) {
                events.add(event);
            }else {
                break;
            }
            */
            //----改造代码（重要）-----
            //若读取到第一个event是null，就说明读取到最后一行，返回空events，则此文件读取结束
            if(i==0 && event==null){
                return events;
            }
            //此时只是读到最后一行，返回events
            if(event==null){
                break;
            }
            //处理每一个event
            Event processEvent = SourceRules.process(event);

            if(!(processEvent ==null)){
                events.add(processEvent);
            }

        }
        //若不是最后一次读取文件，则要加个空bytes，否则文件会一直读下去
        if (events.isEmpty()) {
            events.add(EventBuilder.withBody(new byte[0]));
        }
        //---改造代码---
        return events;
    }

    @Override
    public void mark() throws IOException {
        ensureOpen();
        in.mark();
    }

    @Override
    public void reset() throws IOException {
        ensureOpen();
        in.reset();
    }

    @Override
    public void close() throws IOException {
        if (isOpen) {
            reset();
            in.close();
            isOpen = false;
        }
    }

    private void ensureOpen() {
        if (!isOpen) {
            throw new IllegalStateException("Serializer has been closed");
        }
    }

    // consider not returning a final character that is a high surrogate
    // when truncating
    private String readLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        int readChars = 0;
        while ((c = in.readChar()) != -1) {
            readChars++;

            // FIXME: support \r\n
            if (c == '\n') {
                break;
            }

            sb.append((char) c);

            if (readChars >= maxLineLength) {
                logger.warn("Line length exceeds max ({}), truncating line!",
                        maxLineLength);
                break;
            }
        }

        if (readChars > 0) {
            //源代码
            return sb.toString();


            /*//----改造代码-----
            // 处理每行数据，暂不在此处处理
            String processLine = SourceRules.process(sb.toString());
            //return processLine;
            return processLine;
            //----改造代码-----*/
        } else {
            return null;
        }
    }

    public static class Builder implements EventDeserializer.Builder {

        @Override
        public EventDeserializer build(Context context, ResettableInputStream in) {
            return new CustomizeLineDeserializer(context, in);
        }

    }


    private static class FileInfo {
        private final File file;
        private final long length;
        private final long lastModified;
        private final EventDeserializer deserializer;

        public FileInfo(File file, EventDeserializer deserializer) {
            this.file = file;
            this.length = file.length();
            this.lastModified = file.lastModified();
            this.deserializer = deserializer;
        }

        public long getLength() {
            return length;
        }

        public long getLastModified() {
            return lastModified;
        }

        public EventDeserializer getDeserializer() {
            return deserializer;
        }

        public File getFile() {
            return file;
        }
    }

}
