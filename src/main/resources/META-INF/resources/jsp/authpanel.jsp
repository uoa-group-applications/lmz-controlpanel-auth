<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<h2>Authentication information</h2>

<p>
    You are currently logged in as: ${model.user}
</p>

<p>
    Your roles are: ${model.roles}
</p>
<hr/>

Application supports dumping all request headers to the screen if you add <i>dumpHeaders=true</i> to the request.
To turn this functionality on and off, please use checkbox below
<div style="margin: 20px;">
<c:choose>
    <c:when test="${model.dumpHeaders == 'true'}">
        <input type="checkbox" name="cbDumpHeaders" id="cbDumpHeaders" onclick="LMZPA.cbClicked()" checked/>
    </c:when>

    <c:otherwise>
        <input type="checkbox" name="cbDumpHeaders" id="cbDumpHeaders" onclick="LMZPA.cbClicked()" style="display: inline;">
    </c:otherwise>
</c:choose>
<label style="display: inline;" for="cbDumpHeaders">Allow dumping headers</label>
</div>
<a id="testLink" href="${model.testUrl}" style="display: ${model.dumpHeaders=='true'?'block':'none'}">Test headers</a>


