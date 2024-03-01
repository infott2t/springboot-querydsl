package org.example.firstinstance.controller.firstinstanceurl.domain.userinfo;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.User;
import org.example.domain.user.UserService;
// import Service, Entity, ApiDtoForm.
import org.example.domain.userinfo.UserInfo;
import org.example.domain.userinfo.UserInfoApiDto;
import org.example.domain.userinfo.UserInfoSearchCondition;
import org.example.domain.userinfo.UserInfoService;
import org.example.firstinstance.controller.firstinstanceurl.form.UserInfoApiDtoForm;

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
public class InstanceUrlUserInfoController {

    private final UserInfoService userInfoService;
    private final UserService userService;

    @GetMapping("/administer/instanceurl/userInfo")
    public String index(Model model, UserInfoSearchCondition condition,
                        @RequestParam(value="page", required=false) Integer page,
                        @PageableDefault(size= 10)Pageable pageable) throws Exception {

        Page<UserInfoApiDto> boards = userInfoService.searchAllV2(condition, pageable);


        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        return "firstinstance/userInfo/index";
    }

    @GetMapping("/administer/instanceurl/userInfo/insert")
    public String insert(Model model, UserInfoSearchCondition condition,
                         @RequestParam(value="page", required=false) Integer page,
                         @PageableDefault(size= 10)Pageable pageable) throws Exception{

        Page<UserInfoApiDto> boards = userInfoService.searchAllV2(condition, pageable);


        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        UserInfoApiDtoForm userForm = new UserInfoApiDtoForm();
        model.addAttribute("userForm",userForm);

        return "firstinstance/userInfo/insert";
    }

    @PostMapping("/administer/instanceurl/userInfo/insert_2")
    public String insert_2(Model model, UserInfoApiDtoForm userForm){

        UserInfo userInfo = null;
        User user = null;

        if(userForm.getUserId()!=null) {
            try {
                  user = userService.findById(userForm.getUserId());
            } catch (Exception e) {
                return "redirect:/administer/instanceurl/userInfo/insert";
            }
        }



        try {
            userInfo = new UserInfo();
        DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userInfo.setAddr(userForm.getAddr());
        userInfo.setTel(userForm.getTel());
        userInfo.setEmail(userForm.getEmail());
        userInfo.setPassword(userForm.getPassword());
        userInfo.setIsDel(userForm.getIsDel());
            if(user !=null){userInfo.setUser(user);}
            userInfo.setModifiedDate(LocalDateTime.now());
            userInfo.setCreatedDate(LocalDateTime.now());
            userInfo.setIsDel("N");

            userInfoService.save(userInfo);

        } catch (Exception e) {
        return "redirect:/administer/instanceurl/userInfo/insert";
        }
        return "redirect:/administer/instanceurl/userInfo/insert";}

    @GetMapping("/administer/instanceurl/userInfo/delete")
    public String delete(@RequestParam(value="id")Long id, Model model) {

        UserInfo userInfo = null;
        try {
             userInfo = userInfoService.findById(id);
        } catch (Exception e) {
            return "redirect:/administer/instanceurl/userInfo/";
        }

        userInfo.setIsDel("Y");
        userInfoService.save(userInfo);


        return "redirect:/administer/instanceurl/userInfo/";
    }

    @GetMapping("/administer/instanceurl/userInfo/update")
    public String update(Model model, @RequestParam(value="id")Long id, UserInfoSearchCondition condition,
                         @RequestParam(value="page", required=false) Integer page,
                         @PageableDefault(size= 10)Pageable pageable) throws Exception{
        Page<UserInfoApiDto> boards = userInfoService.searchAllV2(condition, pageable);


        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        UserInfoApiDtoForm userForm = new UserInfoApiDtoForm();
        UserInfo userInfo = null;

        try {
            userInfo = userInfoService.findById(id);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/userInfo/insert";
        }

        userForm.setId(userInfo.getId());
              DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userForm.setId(userInfo.getId());
        userForm.setAddr(userInfo.getAddr());
        userForm.setTel(userInfo.getTel());
        userForm.setEmail(userInfo.getEmail());
        userForm.setPassword(userInfo.getPassword());
        userForm.setIsDel(userInfo.getIsDel());
        if(userInfo.getUser()!=null) {
            userForm.setUserId(userInfo.getUser().getId());
        }

        userForm.setCreatedDate(userInfo.getCreatedDate());
        userForm.setModifiedDate(userInfo.getModifiedDate());

        model.addAttribute("userForm",userForm);
        return "firstinstance/userInfo/update";
    }

    @PostMapping("/administer/instanceurl/userInfo/update_2")
    public String update_2(Model model, @RequestParam(value="id")Long id,UserInfoApiDtoForm userForm, UserInfoSearchCondition condition,
                           @RequestParam(value="page", required=false) Integer page,
                           Pageable pageable) throws Exception {


        UserInfo userInfo = null;
        User user = null;
        try{
            userInfo = userInfoService.findById(id);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/userInfo/insert";
        }

        if(userForm.getUserId()!=null){
            try{
                user = userService.findById(userForm.getUserId());
                userInfo.setUser(user);
            }catch(Exception e){
                return "redirect:/administer/instanceurl/userInfo/insert";
            }
        }
        try{
        DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userInfo.setAddr(userForm.getAddr());
        userInfo.setTel(userForm.getTel());
        userInfo.setEmail(userForm.getEmail());
        userInfo.setPassword(userForm.getPassword());
        userInfo.setIsDel(userForm.getIsDel());
        userInfo.setModifiedDate(LocalDateTime.now());

        userInfoService.save(userInfo);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/userInfo/insert";
        }





        return "redirect:/administer/instanceurl/userInfo/insert";
    }


}
