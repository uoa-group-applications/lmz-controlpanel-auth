package nz.ac.auckland.lmz.controlpanel.bus

import groovy.transform.CompileStatic
import nz.ac.auckland.syllabus.events.Event
import nz.ac.auckland.syllabus.events.EventHandler
import nz.ac.auckland.syllabus.payload.EventRequestBase
import nz.ac.auckland.syllabus.payload.EventResponseBase


/**
 * Event handler for authentication panel. Signals on/off to HeaderDumpFilter (through system property)
 *
 * author: Irina Benediktovich - https://plus.google.com/+IrinaBenediktovich
 */
@Event(name = 'authpanel', namespace = 'admin')
@CompileStatic
class AuthPanelEventHandler implements EventHandler<AuthPanelRequest, AuthPanelResponse> {

	static String FLAG_HEADER_DUMP = "FLAG_HEADER_DUMP"

	@Override
	AuthPanelResponse handleEvent(AuthPanelRequest requestType) throws Exception {
		System.setProperty(FLAG_HEADER_DUMP,  Boolean.valueOf(requestType.dumpHeaderFlag).toString())
		return new AuthPanelResponse()
	}

	static class AuthPanelRequest extends EventRequestBase {
		boolean dumpHeaderFlag
	}

	static class AuthPanelResponse extends EventResponseBase {
		String message = "Okey"
	}
}


