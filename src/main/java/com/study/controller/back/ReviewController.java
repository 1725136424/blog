package com.study.controller.back;

import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.review.Review;
import com.study.pojo.user.User;
import com.study.service.ReviewService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @RequestMapping("/page")
    @ResponseBody
    public PageResult<Review> page(QueryVo vo) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        // 查询需要审核的文字
        PageResult<Review> pageResult = reviewService.findUnAuthenticationReview(vo, user.getId());
        // 填充文章数据
        reviewService.fillArticle(pageResult.getRows());
        return pageResult;
    }

    @RequestMapping("/commitReview")
    @ResponseBody
    public AjaxResult commitReview(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            reviewService.editStatus(id, true);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("审核失败");
        }
        return ajaxResult;
    }

    @RequestMapping("/deleteReview")
    @ResponseBody
    public AjaxResult deleteReview(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            reviewService.delete(id);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }
}
