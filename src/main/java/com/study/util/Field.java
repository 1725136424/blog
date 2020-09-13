package com.study.util;

import com.study.pojo.review.Review;

import java.util.ArrayList;
import java.util.List;

public class Field {
    public static final String salt = "haodada";

    public static final Integer hashIterations = 2;

    public static final String articleCoverRelative = "/static/upload/images/article/cover";

    public static final String bannerRelative = "/static/upload/images/banner";

    public static final String announcementsRelative = "/static/upload/images/announcement";

    public static final String userRelative = "/static/upload/images/user";

    public static final Long normalRole = 2L;

    public static List<Review> treeList = new ArrayList<>();
}
