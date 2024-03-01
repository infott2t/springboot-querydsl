package org.example.firstinstance.controller.firstinstanceurl.domain.posts;
import lombok.RequiredArgsConstructor;
// import Service, Entity, ApiDtoForm.
import org.example.config.auth.LoginUser;
import org.example.config.auth.dto.SessionUser;
import org.example.domain.posts.Posts;
import org.example.domain.posts.PostsApiDto;
import org.example.domain.posts.PostsSearchCondition;
import org.example.domain.posts.PostsService;
import org.example.firstinstance.controller.firstinstanceurl.form.PostsApiDtoForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class InstanceUrlPostsController {

    private final PostsService postsService;

    @GetMapping("/administer/instanceurl/posts")
    public String index(Model model, PostsSearchCondition condition,
                        @RequestParam(value="page", required=false) Integer page,
                        @PageableDefault(size= 10)Pageable pageable, @LoginUser SessionUser user) throws Exception {

        Page<PostsApiDto> boards = postsService.searchAllV2(condition, pageable);

        model.addAttribute("userName", user.getName());
        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        return "firstinstance/posts/index";
    }

    @GetMapping("/administer/instanceurl/posts/insert")
    public String insert(Model model, PostsSearchCondition condition,
                         @RequestParam(value="page", required=false) Integer page,
                         @PageableDefault(size= 10)Pageable pageable, @LoginUser SessionUser user) throws Exception{

        Page<PostsApiDto> boards = postsService.searchAllV2(condition, pageable);


        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        PostsApiDtoForm userForm = new PostsApiDtoForm();
        userForm.setAuthor(user.getName());
        model.addAttribute("userForm",userForm);

        return "firstinstance/posts/insert";
    }

    @PostMapping("/administer/instanceurl/posts/insert_2")
    public String insert_2(Model model, PostsApiDtoForm userForm){

        Posts posts = null;

        try {
            posts = new Posts();
            DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            posts.setTitle(userForm.getTitle());
            posts.setContext(userForm.getContext());
            posts.setAuthor(userForm.getAuthor());
            posts.setIsDel(userForm.getIsDel());
            posts.setModifiedDate(LocalDateTime.now());
            posts.setCreatedDate(LocalDateTime.now());
            posts.setIsDel("N");

            postsService.save(posts);

        } catch (Exception e) {
        return "redirect:/administer/instanceurl/posts/insert";
        }
        return "redirect:/administer/instanceurl/posts/insert";}

    @GetMapping("/administer/instanceurl/posts/delete")
    public String delete(@RequestParam(value="id")Long id, Model model) {

        Posts posts = null;
        try {
             posts = postsService.findById(id);
        } catch (Exception e) {
            return "redirect:/administer/instanceurl/posts/";
        }

        posts.setIsDel("Y");
        postsService.save(posts);


        return "redirect:/administer/instanceurl/posts/";
    }

    @GetMapping("/administer/instanceurl/posts/update")
    public String update(Model model, @RequestParam(value="id")Long id, PostsSearchCondition condition,
                         @RequestParam(value="page", required=false) Integer page,
                         @PageableDefault(size= 10)Pageable pageable) throws Exception{
        Page<PostsApiDto> boards = postsService.searchAllV2(condition, pageable);


        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        PostsApiDtoForm userForm = new PostsApiDtoForm();
        Posts posts = null;

        try {
            posts = postsService.findById(id);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/posts/insert";
        }

        userForm.setId(posts.getId());
              DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userForm.setId(posts.getId());
        userForm.setTitle(posts.getTitle());
        userForm.setContext(posts.getContext());
        userForm.setAuthor(posts.getAuthor());
        userForm.setIsDel(posts.getIsDel());

        userForm.setCreatedDate(posts.getCreatedDate());
        userForm.setModifiedDate(posts.getModifiedDate());

        model.addAttribute("userForm",userForm);
        return "firstinstance/posts/update";
    }

    @PostMapping("/administer/instanceurl/posts/update_2")
    public String update_2(Model model, @RequestParam(value="id")Long id,PostsApiDtoForm userForm, PostsSearchCondition condition,
                           @RequestParam(value="page", required=false) Integer page,
                           Pageable pageable) throws Exception {


        Posts posts = null;
        try{
            posts = postsService.findById(id);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/posts/insert";
        }

        try{
        DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        posts.setTitle(userForm.getTitle());
        posts.setContext(userForm.getContext());
        posts.setAuthor(userForm.getAuthor());
        posts.setIsDel(userForm.getIsDel());
        posts.setModifiedDate(LocalDateTime.now());

        postsService.save(posts);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/posts/insert";
        }





        return "redirect:/administer/instanceurl/posts/insert";
    }


}
