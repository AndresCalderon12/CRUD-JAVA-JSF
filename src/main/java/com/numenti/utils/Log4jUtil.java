package com.numenti.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.numenti.enums.TipoLog;

public class Log4jUtil {
	private static Logger log = Logger.getLogger(Log4jUtil.class);

	private Log4jUtil() {
		throw new IllegalStateException("Utility class");
	}

	@SuppressWarnings("rawtypes")
	public static void registrarInfo(Class clase, TipoLog tipo, String mensaje) {
		log = LogManager.getLogger(clase);

		switch (tipo) {
		case DEBUG:
			log.debug(mensaje);
			break;
		case ERROR:
			log.error(mensaje);
			break;
		case FATAL:
			log.fatal(mensaje);
			break;
		case INFO:
			log.info(mensaje);
			break;
		case WARNING:
			log.warn(mensaje);
		}
	}
}
