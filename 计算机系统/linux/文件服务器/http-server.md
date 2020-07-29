### 1 下载`node.js`

官网地址： [https://nodejs.org](https://nodejs.org/)
```undefined
sudo yum -y install nodejs
```

下载完成后在命令行输入命令`$ node -v`以及`$ npm -v`检查版本，确认是否安装成功。

### 2 下载`http-server`

在终端输入：
`$ npm install http-server -g`

### 3 开启 `http-server`服务

终端进入目标文件夹，然后在终端输入：

```
$ http-server -c-1   （⚠️只输入http-server的话，更新了代码后，页面不会同步更新）


Starting up http-server, serving ./
Available on:
  http://127.0.0.1:8080
  http://192.168.8.196:8080
Hit CTRL-C to stop the server
```

### 4 关闭 `http-server`服务

按快捷键`CTRL-C`
终端显示`^Chttp-server stopped.`即关闭服务成功。









# http-server: a command-line http server

`http-server` is a simple, zero-configuration command-line http server. It is powerful enough for production usage, but it's simple and hackable enough to be used for testing, local development, and learning.

## Installation:

#### Globally via `npm`

```
npm install --global http-server
```

This will install `http-server` globally so that it may be run from the command line anywhere.

#### Globally via Homebrew

```
brew install http-server
```

#### Running on-demand:

Using `npx` you can run the script without installing it first:

```
npx http-server [path] [options]
```

#### As a dependency in your `npm` package:

```
npm install http-server
```

## Usage:

```
 http-server [path] [options]
```

`[path]` defaults to `./public` if the folder exists, and `./` otherwise.

*Now you can visit [http://localhost:8080](http://localhost:8080/) to view your server*

**Note:** Caching is on by default. Add `-c-1` as an option to disable caching.

## Available Options:

`-p` or `--port` Port to use (defaults to 8080)

`-a` Address to use (defaults to 0.0.0.0)

`-d` Show directory listings (defaults to `true`)

`-i` Display autoIndex (defaults to `true`)

`-g` or `--gzip` When enabled (defaults to `false`) it will serve `./public/some-file.js.gz` in place of `./public/some-file.js` when a gzipped version of the file exists and the request accepts gzip encoding. If brotli is also enabled, it will try to serve brotli first.

`-b` or `--brotli` When enabled (defaults to `false`) it will serve `./public/some-file.js.br` in place of `./public/some-file.js` when a brotli compressed version of the file exists and the request accepts `br` encoding. If gzip is also enabled, it will try to serve brotli first.

`-e` or `--ext` Default file extension if none supplied (defaults to `html`)

`-s` or `--silent` Suppress log messages from output

`--cors` Enable CORS via the `Access-Control-Allow-Origin` header

`-o [path]` Open browser window after starting the server. Optionally provide a URL path to open. e.g.: -o /other/dir/

`-c` Set cache time (in seconds) for cache-control max-age header, e.g. `-c10` for 10 seconds (defaults to `3600`). To disable caching, use `-c-1`.

`-U` or `--utc` Use UTC time format in log messages.

`--log-ip` Enable logging of the client's IP address (default: `false`).

`-P` or `--proxy` Proxies all requests which can't be resolved locally to the given url. e.g.: -P [http://someurl.com](http://someurl.com/)

`--username` Username for basic authentication [none]

`--password` Password for basic authentication [none]

`-S` or `--ssl` Enable https.

`-C` or `--cert` Path to ssl cert file (default: `cert.pem`).

`-K` or `--key` Path to ssl key file (default: `key.pem`).

`-r` or `--robots` Provide a /robots.txt (whose content defaults to `User-agent: *\nDisallow: /`)

`--no-dotfiles` Do not show dotfiles

`-h` or `--help` Print this list and exit.

`-v` or `--version` Print the version and exit.

## Magic Files

- `index.html` will be served as the default file to any directory requests.
- `404.html` will be served if a file is not found. This can be used for Single-Page App (SPA) hosting to serve the entry page.

## Catch-all redirect

To implement a catch-all redirect, use the index page itself as the proxy with:

```
http-server --proxy http://localhost:8080?
```

Note the `?` at the end of the proxy URL. Thanks to [@houston3](https://github.com/houston3) for this clever hack!

## TLS/SSL

First, you need to make sure that [openssl](https://github.com/openssl/openssl) is installed correctly