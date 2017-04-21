package com.ducetech.framework.support.service;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MyVelocityToolboxView extends VelocityToolboxView {

	/*
	 * 重写此方法弃用ToolboxManager及ServletToolboxManager以便跟velocity-tools-2.0整合
	 */
	@Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        ViewToolContext velocityContext;

        velocityContext = new ViewToolContext(getVelocityEngine(), request, response, getServletContext());

        velocityContext.putAll(model);

        if (this.getToolboxConfigLocation() != null) {
            ToolManager tm = new ToolManager();
            tm.setVelocityEngine(getVelocityEngine());
            tm.configure(getServletContext().getRealPath(getToolboxConfigLocation()));
            if (tm.getToolboxFactory().hasTools(Scope.REQUEST)) {
            	velocityContext.addToolbox(tm.getToolboxFactory().createToolbox(Scope.REQUEST));
            }
            if (tm.getToolboxFactory().hasTools(Scope.APPLICATION)) {
            	velocityContext.addToolbox(tm.getToolboxFactory().createToolbox(Scope.APPLICATION));
            }
            if (tm.getToolboxFactory().hasTools(Scope.SESSION)) {
            	velocityContext.addToolbox(tm.getToolboxFactory().createToolbox(Scope.SESSION));
            }
        }
        return velocityContext;
    }
}
