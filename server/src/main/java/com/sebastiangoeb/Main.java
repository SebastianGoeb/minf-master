package com.sebastiangoeb;

import static spark.Spark.get;
import static spark.Spark.ipAddress;
import static spark.Spark.port;

import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	private static final int BUFFER_SIZE = 64 * 1024; // 64 KB

	public static void main(String[] args) {
		if (args.length >= 1) {
			ipAddress(args[0]);
		}
		port(8080);
		get("/stop", (req, res) -> {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(100);
						Spark.stop();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
			res.header("Content-Length", "0");
			return "";
		});
		get("/:bytes", (req, res) -> {
			LOG.info("Serving {} to {}", req.params(":bytes"), req.ip());
			
			// Parse params
			long totalSize = human2bytes(req.params(":bytes"));

			// Set headers and status
			res.header("Content-Length", String.valueOf(totalSize));

			// Stream data
			res.raw().setBufferSize(BUFFER_SIZE);
			OutputStream out = res.raw().getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = '-';
			}
			for (long bytesWritten = 0; bytesWritten < totalSize; bytesWritten += Math.min(totalSize - bytesWritten,
					BUFFER_SIZE)) {
				out.write(buffer, 0, (int) Math.min(totalSize - bytesWritten, BUFFER_SIZE));
			}
			return "";
		});
	}

	private static long human2bytes(String humanReadable) {
		if (humanReadable == null) {
			return -1;
		}

		Matcher m = Pattern.compile("^(\\d+)([kKMGTP]?)$").matcher(humanReadable);
		m.matches();
		long bytes = Long.parseLong(m.group(1));
		switch (m.group(2)) {
		case "k":
		case "K":
			bytes <<= 10;
			break;
		case "M":
			bytes <<= 20;
			break;
		case "G":
			bytes <<= 30;
			break;
		case "T":
			bytes <<= 40;
			break;
		case "P":
			bytes <<= 50;
			break;
		}
		return bytes;
	}
}
