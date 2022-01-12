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
 * Short description of the class
 *
 * @author carp
 */
public interface ImgProxyService {

    /**
     * Retrieves processed image from imgProxy.
     * @param url (mandatory)
     * @return response from imgProxy.
     */
    String getProcessedImage(String url);

    /**
     * Generates unsigned url for imgProxy.
     * @param imgPath (mandatory)
     * @param imgSize (mandatory)
     * @param imgConvert (mandatory)
     * @return unsigned url for imgProxy.
     */
    String generateUnsignedUrlForImgProxy(
            String imgPath,
            String imgSize,
            String imgConvert
    );

    /**
     * Generates signed url for imgProxy.
     * @param imgPath (mandatory)
     * @param imgSize (mandatory)
     * @param imgConvert (mandatory)
     * @throws Exception if something wrong during the hash
     * @return signed url for imgProxy.
     */
    String generateSignedUrlForImgProxy(
            String imgPath,
            String imgSize,
            String imgConvert
    ) throws Exception;

}
