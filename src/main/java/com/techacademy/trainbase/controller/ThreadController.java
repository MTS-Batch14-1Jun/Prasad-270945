package com.techacademy.trainbase.controller;

import com.techacademy.trainbase.service.CompletableService;
import com.techacademy.trainbase.threads.VirtualThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/threads")
public class ThreadController {

    @Autowired
    private VirtualThreadService virtualThreadService;

    @Autowired
    private CompletableService completableService;


    @GetMapping
    public ResponseEntity<String> callVirtualThread(){
        virtualThreadService.executeVirtualThread();
        return ResponseEntity.ok("Virtual thread demo completed.");
    }

    @GetMapping("/async")
    public ResponseEntity<String> executeAscThread(){
     //   virtualThreadService.executeAscThread();
      //  virtualThreadService.copyExecuteAscThread();
        completableService.getProjectWithDetailsAsync(1L).thenAccept(projectDTO -> {
            // Handle the project details when they are available
            System.out.println("Current Thread Controller: " + Thread.currentThread());
            System.out.println("Project details: " + projectDTO);
        });
        return ResponseEntity.ok("executeAscThread  completed.");
    }
}
