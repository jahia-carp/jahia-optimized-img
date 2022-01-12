package org.jahia.modules.imgotpimization.filters;

import org.jahia.modules.imgotpimization.services.JahiaOptimizedImgConstants;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;

import net.htmlparser.jericho.*;
import org.jahia.services.render.filter.RenderFilter;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Updates img dom.
 */
@Component(service = RenderFilter.class)
public class ImageFilter extends AbstractFilter {

    @Activate
    public void activate() {
        setPriority(10);
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

        if (!renderContext.getSite().getInstalledModules().contains("jahia-optimized-img")) {
            return previousOut;
        }

        Source html = new Source(previousOut);
        OutputDocument outputDocument = new OutputDocument(html);
        List<Element> images = html.getAllElements("IMG");

        for (Element image : images) {
            StartTag startTag = image.getStartTag();
            Attributes attributes = startTag.getAttributes();
            @Nullable Attribute srcAttribute = attributes.get("src");
            @Nullable Attribute classAttribute = attributes.get("class");

            if ((srcAttribute != null && srcAttribute.hasValue()) && (classAttribute != null && classAttribute.hasValue() && classAttribute.getValue().contains("jahia-optimized"))) {
                String srcValue = srcAttribute.getValue();
                StringBuilder builder = new StringBuilder();
                builder.append("<img").append(" ").append("data-path=\"").append(srcValue).append("\"").append(" ").append("src=\"").append(JahiaOptimizedImgConstants.DEFAULT_LOADING_IMG).append("\"");
                for (Attribute attribute : attributes) {
                    if (!attribute.getKey().equals("src")) {
                        builder.append(" ");
                        builder.append(attribute);
                    }
                }
                builder.append(">");
                outputDocument.replace(startTag, builder);
            }

        }

        return outputDocument.toString();
    }

}
