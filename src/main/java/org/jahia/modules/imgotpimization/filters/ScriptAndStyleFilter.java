/*
 * ==========================================================================================
 * =                            JAHIA'S ENTERPRISE DISTRIBUTION                             =
 * ==========================================================================================
 *
 *                                  http://www.jahia.com
 *
 * JAHIA'S ENTERPRISE DISTRIBUTIONS LICENSING - IMPORTANT INFORMATION
 * ==========================================================================================
 *
 *     Copyright (C) 2002-2022 Jahia Solutions Group. All rights reserved.
 *
 *     This file is part of a Jahia's Enterprise Distribution.
 *
 *     Jahia's Enterprise Distributions must be used in accordance with the terms
 *     contained in the Jahia Solutions Group Terms &amp; Conditions as well as
 *     the Jahia Sustainable Enterprise License (JSEL).
 *
 *     For questions regarding licensing, support, production usage...
 *     please contact our team at sales@jahia.com or go to http://www.jahia.com/license.
 *
 * ==========================================================================================
 */
package org.jahia.modules.imgotpimization.filters;

import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;
import org.jahia.services.render.filter.RenderFilter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Short description of the class
 *
 * @author carp
 */
@Component(service = RenderFilter.class)
public class ScriptAndStyleFilter extends AbstractFilter {

    @Activate
    public void activate() {
        setPriority(11);
        setSkipOnAjaxRequest(true);
        setApplyOnConfigurations("page");
        setApplyOnTemplateTypes("html");
        setApplyOnModes("live");
    }

    @Override
    public String prepare(RenderContext renderContext, Resource resource, RenderChain chain) throws Exception {
        return super.prepare(renderContext, resource, chain);
    }

    @Override
    public String execute(String previousOut, RenderContext renderContext, Resource resource, RenderChain chain) {

        if (renderContext.getSite().getInstalledModules().contains("jahia-optimized-img")) {
            previousOut += ("<jahia:resource type='javascript' path='/modules/jahia-optimized-img/javascript/lazysizes.min.js' insert='false'"
                    + " resource='' title='' key=''/>");

            previousOut += ("<jahia:resource type='javascript' path='/modules/jahia-optimized-img/javascript/jahia-img-optimized.js' "
                    + "insert='false' resource='' title='' key=''/>");

            previousOut += ("<jahia:resource type='css' path='/modules/jahia-optimized-img/css/jahia-img-optimized.css' insert='false' resource='' title='' key=''/>");
        }

        return previousOut;

    }

}
