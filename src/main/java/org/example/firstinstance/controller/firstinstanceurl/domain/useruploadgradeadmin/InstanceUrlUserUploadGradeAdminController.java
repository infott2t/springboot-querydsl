package org.example.firstinstance.controller.firstinstanceurl.domain.useruploadgradeadmin;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.User;
import org.example.domain.user.UserService;
// import Service, Entity, ApiDtoForm.
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdmin;
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdminApiDto;
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdminSearchCondition;
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdminService;
import org.example.firstinstance.controller.firstinstanceurl.form.UserUploadGradeAdminApiDtoForm;

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
public class InstanceUrlUserUploadGradeAdminController {

    private final UserUploadGradeAdminService userUploadGradeAdminService;
    private final UserService userService;

    @GetMapping("/administer/instanceurl/userUploadGradeAdmin")
    public String index(Model model, UserUploadGradeAdminSearchCondition condition,
                        @RequestParam(value="page", required=false) Integer page,
                        @PageableDefault(size= 10)Pageable pageable) throws Exception {

        Page<UserUploadGradeAdminApiDto> boards = userUploadGradeAdminService.searchAllV2(condition, pageable);


        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        return "firstinstance/userUploadGradeAdmin/index";
    }

    @GetMapping("/administer/instanceurl/userUploadGradeAdmin/insert")
    public String insert(Model model, UserUploadGradeAdminSearchCondition condition,
                         @RequestParam(value="page", required=false) Integer page,
                         @PageableDefault(size= 10)Pageable pageable) throws Exception{

        Page<UserUploadGradeAdminApiDto> boards = userUploadGradeAdminService.searchAllV2(condition, pageable);


        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        UserUploadGradeAdminApiDtoForm userForm = new UserUploadGradeAdminApiDtoForm();
        model.addAttribute("userForm",userForm);

        return "firstinstance/userUploadGradeAdmin/insert";
    }

    @PostMapping("/administer/instanceurl/userUploadGradeAdmin/insert_2")
    public String insert_2(Model model, UserUploadGradeAdminApiDtoForm userForm){

        UserUploadGradeAdmin userUploadGradeAdmin = null;
        User user = null;

        if(userForm.getUserId()!=null) {
            try {
                  user = userService.findById(userForm.getUserId());
            } catch (Exception e) {
                return "redirect:/administer/instanceurl/userUploadGradeAdmin/insert";
            }
        }



        try {
            userUploadGradeAdmin = new UserUploadGradeAdmin();
        DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userUploadGradeAdmin.setEmail(userForm.getEmail());
        userUploadGradeAdmin.setCertNum(userForm.getCertNum());
        userUploadGradeAdmin.setOriginFile(userForm.getOriginFile());
        userUploadGradeAdmin.setUuidPath(userForm.getUuidPath());
        userUploadGradeAdmin.setWhoPermit(userForm.getWhoPermit());
        userUploadGradeAdmin.setIsPermit(userForm.getIsPermit());
        userUploadGradeAdmin.setIsDel(userForm.getIsDel());
            if(user !=null){userUploadGradeAdmin.setUser(user);}
            userUploadGradeAdmin.setModifiedDate(LocalDateTime.now());
            userUploadGradeAdmin.setCreatedDate(LocalDateTime.now());
            userUploadGradeAdmin.setIsDel("N");

            userUploadGradeAdminService.save(userUploadGradeAdmin);

        } catch (Exception e) {
        return "redirect:/administer/instanceurl/userUploadGradeAdmin/insert";
        }
        return "redirect:/administer/instanceurl/userUploadGradeAdmin/insert";}

    @GetMapping("/administer/instanceurl/userUploadGradeAdmin/delete")
    public String delete(@RequestParam(value="id")Long id, Model model) {

        UserUploadGradeAdmin userUploadGradeAdmin = null;
        try {
             userUploadGradeAdmin = userUploadGradeAdminService.findById(id);
        } catch (Exception e) {
            return "redirect:/administer/instanceurl/userUploadGradeAdmin/";
        }

        userUploadGradeAdmin.setIsDel("Y");
        userUploadGradeAdminService.save(userUploadGradeAdmin);


        return "redirect:/administer/instanceurl/userUploadGradeAdmin/";
    }

    @GetMapping("/administer/instanceurl/userUploadGradeAdmin/update")
    public String update(Model model, @RequestParam(value="id")Long id, UserUploadGradeAdminSearchCondition condition,
                         @RequestParam(value="page", required=false) Integer page,
                         @PageableDefault(size= 10)Pageable pageable) throws Exception{
        Page<UserUploadGradeAdminApiDto> boards = userUploadGradeAdminService.searchAllV2(condition, pageable);


        model.addAttribute("boards", boards);
        model.addAttribute("condition", condition);
        model.addAttribute("page", pageable.getPageNumber()+1); // 0부터 시작, +1이 필요.

        UserUploadGradeAdminApiDtoForm userForm = new UserUploadGradeAdminApiDtoForm();
        UserUploadGradeAdmin userUploadGradeAdmin = null;

        try {
            userUploadGradeAdmin = userUploadGradeAdminService.findById(id);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/userUploadGradeAdmin/insert";
        }

        userForm.setId(userUploadGradeAdmin.getId());
              DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userForm.setId(userUploadGradeAdmin.getId());
        userForm.setEmail(userUploadGradeAdmin.getEmail());
        userForm.setCertNum(userUploadGradeAdmin.getCertNum());
        userForm.setOriginFile(userUploadGradeAdmin.getOriginFile());
        userForm.setUuidPath(userUploadGradeAdmin.getUuidPath());
        userForm.setWhoPermit(userUploadGradeAdmin.getWhoPermit());
        userForm.setIsPermit(userUploadGradeAdmin.getIsPermit());
        userForm.setIsDel(userUploadGradeAdmin.getIsDel());
        if(userUploadGradeAdmin.getUser()!=null) {
            userForm.setUserId(userUploadGradeAdmin.getUser().getId());
        }

        userForm.setCreatedDate(userUploadGradeAdmin.getCreatedDate());
        userForm.setModifiedDate(userUploadGradeAdmin.getModifiedDate());

        model.addAttribute("userForm",userForm);
        return "firstinstance/userUploadGradeAdmin/update";
    }

    @PostMapping("/administer/instanceurl/userUploadGradeAdmin/update_2")
    public String update_2(Model model, @RequestParam(value="id")Long id,UserUploadGradeAdminApiDtoForm userForm, UserUploadGradeAdminSearchCondition condition,
                           @RequestParam(value="page", required=false) Integer page,
                           Pageable pageable) throws Exception {


        UserUploadGradeAdmin userUploadGradeAdmin = null;
        User user = null;
        try{
            userUploadGradeAdmin = userUploadGradeAdminService.findById(id);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/userUploadGradeAdmin/insert";
        }

        if(userForm.getUserId()!=null){
            try{
                user = userService.findById(userForm.getUserId());
                userUploadGradeAdmin.setUser(user);
            }catch(Exception e){
                return "redirect:/administer/instanceurl/userUploadGradeAdmin/insert";
            }
        }
        try{
        DateTimeFormatter stdFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userUploadGradeAdmin.setEmail(userForm.getEmail());
        userUploadGradeAdmin.setCertNum(userForm.getCertNum());
        userUploadGradeAdmin.setOriginFile(userForm.getOriginFile());
        userUploadGradeAdmin.setUuidPath(userForm.getUuidPath());
        userUploadGradeAdmin.setWhoPermit(userForm.getWhoPermit());
        userUploadGradeAdmin.setIsPermit(userForm.getIsPermit());
        userUploadGradeAdmin.setIsDel(userForm.getIsDel());
        userUploadGradeAdmin.setModifiedDate(LocalDateTime.now());

        userUploadGradeAdminService.save(userUploadGradeAdmin);
        }catch(Exception e){
            return "redirect:/administer/instanceurl/userUploadGradeAdmin/insert";
        }





        return "redirect:/administer/instanceurl/userUploadGradeAdmin/insert";
    }


}
