package nz.ac.auckland.lmz.controlpanel.panels

import nz.ac.auckland.common.stereotypes.UniversityComponent
import nz.ac.auckland.jetty.RoleSource
import nz.ac.auckland.lmz.controlpanel.DefaultControlPanelAssets
import nz.ac.auckland.lmz.controlpanel.bus.AuthPanelEventHandler
import nz.ac.auckland.lmz.controlpanel.core.ControlPanel
import nz.ac.auckland.lmz.controlpanel.core.ControlPanelAssets
import nz.ac.auckland.lmz.controlpanel.core.ControlPanelMetadata
import nz.ac.auckland.stencil.LinkBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import javax.inject.Inject
import javax.servlet.http.HttpServletRequest

/**
 * Renders Authentication Control Panel
 *
 * author: Irina Benediktovich - https://plus.google.com/+IrinaBenediktovich
 */
@UniversityComponent
class AuthContolpanel implements ControlPanel {

	private Logger log = LoggerFactory.getLogger(getClass())

	@Inject RoleSource roleSource
	@Inject DefaultControlPanelAssets defaultControlPanelAssets
	@Inject LinkBuilder linkBuilder

	/**
	 * @return the control panel meta data
	 */
	@Override
	ControlPanelMetadata getMetadata() {

		return new ControlPanelMetadata(
				title: "Auth Console",
				description: "Displaying currently logged user and roles",
				uri: "auth-console",
				assets: new ControlPanelAssets(
						javascripts: ['/js/authpanel.js'],
						embeds: [defaultControlPanelAssets as ControlPanelAssets])
		);
	}

	public HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes servletRequestAttributes =
				RequestContextHolder.requestAttributes as ServletRequestAttributes

		HttpServletRequest request = servletRequestAttributes?.request

		return request
	}


	/**
	 * @return the template to render
	 */
	@Override
	String getTemplate() {
		return "/jsp/authpanel.jsp";
	}

	/**
	 * TODO think of mechanism to detect presence of HeaderDumpFilter
	 * @return current user information (name, roles), dumpHeader flag.
	 */
	@Override
	Map<String, Object> getViewModel() {
		String user = getCurrentRequest().getRemoteUser()
		def roles = roleSource.getRoles(user, getCurrentRequest());
		return [ user: user,
				roles: "${splitByX(roles.sort(), 5)}",
				dumpHeaders: System.getProperty(AuthPanelEventHandler.FLAG_HEADER_DUMP),
				testUrl: (linkBuilder.linkTo(getCurrentRequest().getRequestURI())+"?dumpHeaders=true").replaceAll('//','/')
		]
	}

	/**
	 * Splits list of strings into several blocks of given size.
	 * Example of usage: to display no more than 5 items on a line.
	 *
	 * TODO Rewrite to keep them sorted
	 *
	 * @param items
	 * @param numberOnLine
	 * @return
	 */
	public static String splitByX(List<String> items, Integer numberOnLine){
		if (!items) return ''
		Map split = items.groupBy {
			return  Math.round ((double)items.indexOf(it)/numberOnLine + 0.5)
		}
		def lines = split.collect {key, value -> value.join(', ') }
		return lines.join('\n')
	}

}
