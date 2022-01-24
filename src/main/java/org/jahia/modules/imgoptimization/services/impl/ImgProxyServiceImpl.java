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
package org.jahia.modules.imgoptimization.services.impl;

import org.jahia.modules.imgoptimization.services.ImgProxyService;
import org.jahia.modules.imgoptimization.services.JahiaOptimizedImgConstants;
import org.jahia.services.notification.HttpClientService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Short description of the class
 *
 * @author carp
 */
@Component(service = ImgProxyService.class, immediate = true)
public class ImgProxyServiceImpl implements ImgProxyService {

    private HttpClientService httpClientService;
    private final String HMACSHA256 = "HmacSHA256";

    private final byte[] key = hexStringToByteArray(JahiaOptimizedImgConstants.IMGPROXY_KEY);
    private final byte[] salt = hexStringToByteArray(JahiaOptimizedImgConstants.IMGPROXY_SALT);

    @Reference
    public void setHttpClientService(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }

    public String getProcessedImage(String url) {
        return httpClientService.executeGet(url);
    }

    public String generateUnsignedUrlForImgProxy(String imgPath, String imgSize, String imgConvert, String currentBaseUrl) {

        String url = "";

        if (imgConvert.equals("true")) {
            imgConvert = "@webp";
        } else {
            imgConvert = "";
        }

        if (imgPath.startsWith("http")) {
            url = JahiaOptimizedImgConstants.IMGPROXY_UNSIGNED_BASE_URL + "/resize:fit:" + imgSize + ":0:0/plain/" + imgPath + imgConvert;
        } else {
            url = JahiaOptimizedImgConstants.IMGPROXY_UNSIGNED_BASE_URL + "/resize:fit:" + imgSize + ":0:0/plain/" + currentBaseUrl + imgPath + imgConvert;
        }

        return url;
    }

    public String generateSignedUrlForImgProxy(String imgPath, String imgSize, String imgConvert, String currentBaseUrl) throws Exception {

        String url = "";
        String path = "";

        if (imgConvert.equals("true")) {
            imgConvert = "@webp";
        } else {
            imgConvert = "";
        }

        if (imgPath.startsWith("http")) {
            path = "/resize:fit:" + imgSize + ":0:0/plain/" + imgPath + imgConvert;
        } else {
            path = "/resize:fit:" + imgSize + ":0:0/plain/" + currentBaseUrl + imgPath + imgConvert;
        }

        Mac sha256HMAC = Mac.getInstance(HMACSHA256);
        SecretKeySpec secretKey = new SecretKeySpec(key, HMACSHA256);
        sha256HMAC.init(secretKey);
        sha256HMAC.update(salt);

        String hash = Base64.getUrlEncoder().withoutPadding().encodeToString(sha256HMAC.doFinal(path.getBytes()));

        url = JahiaOptimizedImgConstants.IMGPROXY_BASE_URL + "/" + hash + path;

        return url;
    }

    private byte[] hexStringToByteArray(String hex){
        if (hex.length() % 2 != 0)
            throw new IllegalArgumentException("Even-length string required");
        byte[] res = new byte[hex.length() / 2];
        for (int i = 0; i < res.length; i++) {
            res[i]=(byte)((Character.digit(hex.charAt(i * 2), 16) << 4) | (Character.digit(hex.charAt(i * 2 + 1), 16)));
        }
        return res;
    }

}
