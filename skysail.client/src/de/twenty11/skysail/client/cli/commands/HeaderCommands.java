package de.twenty11.skysail.client.cli.commands;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class HeaderCommands implements CommandMarker {

	@Autowired
	private Context context;

	@CliCommand(value = "setHeader", help = "sets existing or adds new header to be used in subsequent requests")
	public String get(
			@CliOption(key = { "", "header" }, mandatory = false, help = "e.g. setHeader Accept=text/html") final Header header,
			@CliOption(key = { "Accept" }, mandatory = false, help = "Content-Types that are acceptable for the response, e.g. 'text/plain'") final String accept,
			@CliOption(key = { "Accept-Charset" }, mandatory = false, help = "Character sets that are acceptable, e.g. 'utf-8'") final String acceptCharset,
			@CliOption(key = { "Accept-Encoding" }, mandatory = false, help = "List of acceptable encodings. See HTTP compression., e.g. 'gzip, deflate'") final String acceptEncoding,
			@CliOption(key = { "Accept-Language" }, mandatory = false, help = "List of acceptable human languages for response, e.g. 'en-US'") final String acceptLanguage,
			@CliOption(key = { "Accept-Datetime" }, mandatory = false, help = "Acceptable version in time, example: 'Thu, 31 May 2007 20:35:00 GMT'") final String acceptDatetime,
			@CliOption(key = { "Authorization" }, mandatory = false, help = "Authentication credentials for HTTP authentication, e.g. 'Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=='") final String authorization,
			@CliOption(key = { "Cache-Control" }, mandatory = false, help = "Used to specify directives that must be obeyed by all caching mechanisms along the request-response chain, e.g. 'no-cache'") final String cacheControl,
			@CliOption(key = { "Connection" }, mandatory = false, help = "What type of connection the user-agent would prefer, e.g 'keep-alive'") final String connection,
			@CliOption(key = { "Cookie" }, mandatory = false, help = "An HTTP cookie previously sent by the server with Set-Cookie, e.g '$Version=1; Skin=new;'") final String cookie,
			@CliOption(key = { "Content-Length" }, mandatory = false, help = "The length of the request body in octets (8-bit bytes)") final String contentLength,
			@CliOption(key = { "Content-MD5" }, mandatory = false, help = "A Base64-encoded binary MD5 sum of the content of the request body") final String contentMD5,
			@CliOption(key = { "Content-Type" }, mandatory = false, help = "The MIME type of the body of the request (used with POST and PUT requests)") final String contentType,
			@CliOption(key = { "Date" }, mandatory = false, help = "The date and time that the message was sent (in 'HTTP-date' format as defined by RFC 7231)") final String date,
			@CliOption(key = { "Expect" }, mandatory = false, help = " 	Indicates that particular server behaviors are required by the client") final String expect,
			@CliOption(key = { "From" }, mandatory = false, help = "The email address of the user making the request") final String from,
			@CliOption(key = { "Host" }, mandatory = false, help = "The domain name of the server (for virtual hosting), and the TCP port number on which the server is listening. The port number may be omitted if the port is the standard port for the service requested.") final String host,
			@CliOption(key = { "If-Match" }, mandatory = false, help = "Only perform the action if the client supplied entity matches the same entity on the server. This is mainly for methods like PUT to only update a resource if it has not been modified since the user last updated it.") final String ifMatch,
			@CliOption(key = { "If-Modified-Since" }, mandatory = false, help = "Allows a 304 Not Modified to be returned if content is unchanged") final String ifModifiedSince,
			@CliOption(key = { "If-None-Match" }, mandatory = false, help = "Allows a 304 Not Modified to be returned if content is unchanged, see HTTP ETag") final String ifNoneMatch,
			@CliOption(key = { "If-Range" }, mandatory = false, help = "If the entity is unchanged, send me the part(s) that I am missing; otherwise, send me the entire new entity") final String ifRange,
			@CliOption(key = { "If-Unmodified-Since" }, mandatory = false, help = "Only send the response if the entity has not been modified since a specific time.") final String ifUnmodifiedSince,
			@CliOption(key = { "Max-Forwards" }, mandatory = false, help = "Limit the number of times the message can be forwarded through proxies or gateways.") final String maxForwards,
			@CliOption(key = { "Origin" }, mandatory = false, help = "Initiates a request for cross-origin resource sharing (asks server for an 'Access-Control-Allow-Origin' response field) .") final String origin,
			@CliOption(key = { "Pragma" }, mandatory = false, help = "Implementation-specific fields that may have various effects anywhere along the request-response chain.") final String pragma,
			@CliOption(key = { "Proxy-Authorization" }, mandatory = false, help = "Authorization credentials for connecting to a proxy.") final String proxyAuthorization,
			@CliOption(key = { "Range" }, mandatory = false, help = "Request only part of an entity. Bytes are numbered from 0. See Byte serving.") final String range,
			@CliOption(key = { "Referer" }, mandatory = false, help = "This is the address of the previous web page from which a link to the currently requested page was followed.") final String referer,
			@CliOption(key = { "TE" }, mandatory = false, help = "The transfer encodings the user agent is willing to accept") final String te,
			@CliOption(key = { "User-Agent" }, mandatory = false, help = "The user agent string of the user agent") final String userAgent,
			@CliOption(key = { "Upgrade" }, mandatory = false, help = "Ask the server to upgrade to another protocol.") final String upgrade,
			@CliOption(key = { "Via" }, mandatory = false, help = "Informs the server of proxies through which the request was sent.") final String via,
			@CliOption(key = { "Warning" }, mandatory = false, help = "A general warning about possible problems with the entity body.") final String warning,
			@CliOption(key = { "X-Requested-With" }, mandatory = false, help = "mainly used to identify Ajax requests. Most JavaScript frameworks send this field with value of XMLHttpRequest") final String xRequestedWith,
			@CliOption(key = { "DNT" }, mandatory = false, help = "Requests a web application to disable their tracking of a user. ") final String dnt,
			@CliOption(key = { "X-Forwarded-For" }, mandatory = false, help = "a de facto standard for identifying the originating IP address of a client connecting to a web server through an HTTP proxy or load balancer") final String xForwardedFor,
			@CliOption(key = { "X-Forwarded-Proto" }, mandatory = false, help = "a de facto standard for identifying the originating protocol of an HTTP request, since a reverse proxy (load balancer) may communicate with a web server using HTTP even if the request to the reverse proxy is HTTPS") final String xForwardedProto,
			@CliOption(key = { "Front-End-Https" }, mandatory = false, help = "Non-standard header field used by Microsoft applications and load-balancers") final String frontEndHttps,
			@CliOption(key = { "X-ATT-DeviceId" }, mandatory = false, help = "Allows easier parsing of the MakeModel/Firmware that is usually found in the User-Agent String of AT&T Devices") final String xATTDeviceId,
			@CliOption(key = { "X-Wap-Profile" }, mandatory = false, help = "Links to an XML file on the Internet with a full description and details about the device currently connecting. In the example to the right is an XML file for an AT&T Samsung Galaxy S2.") final String xWapProfile,
			@CliOption(key = { "Proxy-Connection" }, mandatory = false, help = "Implemented as a misunderstanding of the HTTP specifications. Common because of mistakes in implementations of early HTTP versions. Has exactly the same functionality as standard Connection field.") final String proxyConnection) {
		handle("Accept", accept);
		handle("Accept-Charset", acceptCharset);
		handle("Accept-Encoding", acceptEncoding);
		handle("Accept-Language", acceptLanguage);
		handle("Accept-Datetime", acceptDatetime);
		handle("Authorization", authorization);
		handle("Cache-Control", cacheControl);
		handle("Connection", connection);
		handle("Cookie", cookie);
		handle("Content-Length", contentLength);
		handle("Content-MD5", contentMD5);
		handle("Content-Type", contentType);
		handle("Date", date);
		handle("Expect", expect);
		handle("From", from);
		handle("Host", host);
		handle("If-Match", ifMatch);
		handle("If-Modified-Since", ifModifiedSince);
		handle("If-None-Match", ifNoneMatch);
		handle("If-Range", ifRange);
		handle("If-Unmodified-Since", ifUnmodifiedSince);
		handle("Max-Forwards", maxForwards);
		handle("Origin", origin);
		handle("Pragma", pragma);
		handle("Proxy-Authorization", proxyAuthorization);
		handle(" Range", range);
		handle("Referer", referer);
		handle("TE", te);
		handle("User-Agent", userAgent);
		handle("Upgrade", upgrade);
		handle("Via", via);
		handle("Warning", warning);
		handle("X-Requested-With", xRequestedWith);
		handle("DNT", dnt);
		handle("X-Forwarded-For", xForwardedFor);
		handle("X-Forwarded-Proto", xForwardedProto);
		handle("Front-End-Https", frontEndHttps);
		handle("X-ATT-DeviceId", xATTDeviceId);
		handle("X-Wap-Profile", xWapProfile);
		handle("Proxy-Connection", proxyConnection);
		if (header != null) {
			context.addOrSetHeader(header);
		}
		return "Request headers set to: " + context.getRequestHeaders();
	}

	private void handle(String name, String value) {
		if (value == null) {
			return;
		}
		context.addOrSetHeader(new BasicHeader(name, value));
	}

	@CliCommand(value = "removeHeader", help = "removes header from subsequent requests")
	public String remove(
			@CliOption(key = { "", "header" }, mandatory = false, help = "e.g. removeHeader Accept") final String header) {
		context.removeHeader(header);
		return "Request headers set to: " + context.getRequestHeaders();
	}

	@CliCommand(value = "showHeader", help = "shows request headers for subsequent requests")
	public String show() {
		return "Current Request headers: " + context.getRequestHeaders();
	}

}
