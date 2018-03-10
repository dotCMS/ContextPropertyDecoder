package com.dotcms.tomcat.valve;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.AbstractAccessLogValve;
import org.pmw.tinylog.Logger;

import com.dotcms.business.CloseDBIfOpened;
import com.dotcms.visitor.domain.Visitor;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.filters.Constants;
import com.dotmarketing.util.WebKeys;

public class DotCMSAccessLogValue extends AbstractAccessLogValve {

  @Override
  protected void log(final CharArrayWriter message) {



  }

  @CloseDBIfOpened
  @Override
  public void invoke(Request request, Response response) throws IOException, ServletException {
    StringWriter sw = new StringWriter();



    Visitor visitor = APILocator.getVisitorAPI().getVisitor(request).get();


    sw.append("ip:")
    .append(String.valueOf(visitor.getIpAddress()))
    .append('\t')
    .append("time:")
    .append(String.valueOf(System.currentTimeMillis()))
    .append('\t')
    .append("dmid:")
    .append(String.valueOf(visitor.getDmid()))
    .append('\t')
    .append("uri:")
    .append(String.valueOf(request.getAttribute(javax.servlet.RequestDispatcher.FORWARD_REQUEST_URI)))
    .append('\t')
    .append("referer:")
    .append(request.getHeader("referer"))
    .append('\t')
    .append("host:")
    .append(request.getHeader("host"))
    .append('\t')
    .append("pageId:")
    .append(Constants.CMS_FILTER_URI_OVERRIDE)
    .append('\t')
    .append("contentId:")
    .append((String) request.getAttribute(WebKeys.WIKI_CONTENTLET));
    Logger.info(sw);
  }

  @Override
  public boolean getRequestAttributesEnabled() {
    return Boolean.TRUE;
  }



}
