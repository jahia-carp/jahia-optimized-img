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
package org.jahia.modules.imgoptimization.servlets;
import org.apache.commons.lang.StringUtils;
import org.jahia.modules.imgoptimization.services.ImgProxyService;
import org.jahia.modules.imgoptimization.services.JahiaOptimizedImgConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.URI;
import java.util.stream.Stream;

/**
 * Short description of the class
 *
 * @author carp
 */

@Component(service = {javax.servlet.http.HttpServlet.class, javax.servlet.Servlet.class}, property = {"alias=/ImageSecureUrl"})
public class ImageSecureUrlServlet extends HttpServlet {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ImageSecureUrlServlet.class);
    private ImgProxyService imgProxyService;

    @Reference
    public void setImageGetService(ImgProxyService imgProxyService) {
        this.imgProxyService = imgProxyService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String imgPath = request.getParameter("path");
        String imgSize = request.getParameter("size");
        String imgConvert = request.getParameter("convert");
        String url = "";

        if (Stream.of(imgPath, imgSize, imgConvert).anyMatch(StringUtils::isEmpty)) {
            return;
        }

        URI baseUri = URI.create(request.getRequestURL().toString());
        String currentBaseUrl = baseUri.getScheme() + "://" + baseUri.getHost() + (baseUri.getPort() > 0 ? (":" + baseUri.getPort()) : "");

        if (JahiaOptimizedImgConstants.IMGPROXY_SIGNED_URL_ENABLED) {

            try {
                url = imgProxyService.generateSignedUrlForImgProxy(imgPath, imgSize, imgConvert, currentBaseUrl);
                logger.info("SIGNED URL: {}", url);
            } catch (Exception e) {
                logger.error(String.valueOf(e));
            }

        } else {

            url = imgProxyService.generateUnsignedUrlForImgProxy(imgPath, imgSize, imgConvert, currentBaseUrl);

        }

        String myResponse = imgProxyService.getProcessedImage(url);

        if (myResponse == null || myResponse.isEmpty()) {
            return;
        }

        InputStream in = new ByteArrayInputStream(myResponse.getBytes("ISO-8859-1"));

        OutputStream out = response.getOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) >= 0)
        {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();

    }
}
