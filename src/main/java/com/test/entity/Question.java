package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String title;
    private List<String> answers;
    private Set<Integer> rightAnswers;

}
