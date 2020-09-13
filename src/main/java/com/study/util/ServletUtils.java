package com.study.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ServletUtils {

    private HttpSession httpSession;

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        if (httpSession != null) {
            this.httpSession = httpSession;
        }
    }

    public static String getAbsolutePath(HttpServletRequest request) {
       return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()  + request.getContextPath();
    }

    public static String uploadImage(MultipartFile image, HttpServletRequest request, String targetFile) throws IOException {
        // 上传图片
        String absolutePath = request.getSession()
                .getServletContext()
                .getRealPath(targetFile);
        String randomStr = UUID.randomUUID().toString().replace("-", "");
        String sufferImg = StringUtils.substringAfterLast(image.getOriginalFilename(), ".");
        String img = randomStr + "." + sufferImg;
        String imagePath = absolutePath + "/" + img;
        File file = new File(imagePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        String httpImage = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + request.getContextPath() +
                targetFile + "/" + img;
        return httpImage;
    }
}
