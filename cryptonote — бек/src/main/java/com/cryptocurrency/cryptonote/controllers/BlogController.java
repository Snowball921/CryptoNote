package com.cryptocurrency.cryptonote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.cryptocurrency.cryptonote.rep.postrepository;
import com.cryptocurrency.cryptonote.models.post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private postrepository postrepository;

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }
    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<post> post = postrepository.findAll();
        model.addAttribute("post", post);
        return "blog-main";
    }


    @PostMapping("/blog/add")
    public String BlogPostAdd(@RequestParam String title,@RequestParam String anons,@RequestParam String text, Model model){
        post post =new post(title,anons,text);
        postrepository.save(post);
        return "redirect:/blog";

    }
    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id")long id,Model model){
        if(!postrepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<post> post = postrepository.findById(id);
        ArrayList<post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id")long id,Model model){
        if(!postrepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<post> post = postrepository.findById(id);
        ArrayList<post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String BlogPostUpdate(@PathVariable(value = "id") long id,@RequestParam String title,@RequestParam String anons,@RequestParam String text, Model model){

        post post = postrepository.findById(id).orElse(null);;

        post.setTitle(title);
        post.setAnons(anons);
        post.setText(text);
        postrepository.save(post);
        return "redirect:/blog";
    }



    @PostMapping("/blog/{id}/remove")
    public String BlogPostDelete(@PathVariable(value = "id") long id, Model model){
        post post = postrepository.findById(id).orElse(null);;
        postrepository.delete(post);

        return "redirect:/blog";

    }
}
