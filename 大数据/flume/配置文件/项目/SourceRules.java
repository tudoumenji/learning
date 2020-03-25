/**
 * 需要进行过滤的规则
 */
public class SourceRules {
    private static final Logger logger = LoggerFactory.getLogger(SourceRules.class);
    private static String regular;
    private static Pattern regex;
    private static PyFunction pyFunction;

    static {
        //获取python脚本，并生成python函数解释器
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("/xx/xx/flume/test/xx/py/xx.py");
        pyFunction = interpreter.get("readCommandDetail", PyFunction.class);

        regular = "regular";
        regex = Pattern.compile("<xmp>\\+?\\[(\\d+-\\d+-\\d+.+)\\][【\\[].+[】\\]]\\[(\\d+\\.\\d+\\.\\d+\\.\\d+\\s?\\d*)\\]\\s*:\\s*(.*?)(</xmp>)");

    }

    /**
     * 根据规则处理event
     *
     * @param event event
     * @return event
     */
    public static Event process(Event event) {
        Matcher matcher = regex.matcher(new String(event.getBody()));
        if (matcher.find()) {
            event.setBody(Bytes.concat(
                    event.getHeaders().get("file").getBytes(),
                    "++".getBytes(),
                    matcher.group().getBytes()
            ));
        } else {
            //注意，所有不符合的event返回null，即可
            event = null;
        }
        return event;
    }

    /**
     * 根据python脚本处理event
     *
     * @param event event
     * @return event
     */
    public static Event processByPythonScript(Event event) throws UnsupportedEncodingException {

        PyString pyString = Py.newStringUTF8(new String(event.getBody(), "utf-8"));
        //调取python函数解析器，解析每一个event
        PyObject pyobj = pyFunction.__call__(pyString);
        //pyobj.__nonzero__()未true，则代表此event符合规则
        if (pyobj.__nonzero__()) {
            JSONObject object = JSONObject.parseObject(pyobj.toString());
            event.setBody(Bytes.concat(
                    event.getHeaders().get("file").getBytes(),
                    "++".getBytes(),
                    object.toString().getBytes()
            ));
            return event;
        } else {
            return null;
        }
    }
}
