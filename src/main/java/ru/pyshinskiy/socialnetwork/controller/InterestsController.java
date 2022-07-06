package ru.pyshinskiy.socialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.pyshinskiy.socialnetwork.entity.Interest;
import ru.pyshinskiy.socialnetwork.service.InterestService;

import java.sql.SQLException;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/interest")
public class InterestsController {

    private final InterestService interestService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<Interest>> getInterests() throws SQLException {
        return ResponseEntity.ok().body(interestService.getAll());
    }

}
