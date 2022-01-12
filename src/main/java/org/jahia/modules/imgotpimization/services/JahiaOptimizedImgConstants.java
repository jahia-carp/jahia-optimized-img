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
package org.jahia.modules.imgotpimization.services;

/**
 * Constants use to configure the module
 * TODO use karaf config file instead
 *
 * @author carp
 */
public class JahiaOptimizedImgConstants {
    public static final String DEFAULT_LOADING_IMG = "data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=";

    public static final Boolean IMGPROXY_SIGNED_URL_ENABLED = false;
    public static final String IMGPROXY_KEY = "943b421c9eb07c830af81030552c86009268de4e532ba2ee2eab8247c6da0881";
    public static final String IMGPROXY_SALT = "520f986b998545b4785e0defbc4f3c1203f22de2374a3d53cb7a7fe9fea309c5";
    public static final String IMGPROXY_BASE_URL = "http://localhost:8888";
    public static final String IMGPROXY_UNSIGNED_BASE_URL = IMGPROXY_BASE_URL + "/insecure";

    // Very dirty hack to remove juste after the demo.
    public static final String LOCAL_URL = "http://rgauthier.preview.sandbox.jahia.com:8080";

    private JahiaOptimizedImgConstants() {
    }
}