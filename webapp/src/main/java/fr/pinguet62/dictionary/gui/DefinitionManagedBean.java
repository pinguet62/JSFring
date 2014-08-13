package fr.pinguet62.dictionary.gui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

import org.springframework.beans.factory.annotation.Autowired;

import fr.pinguet62.dictionary.model.Keyword;
import fr.pinguet62.dictionary.service.KeywordService;

public final class DefinitionManagedBean {

    @ManagedProperty(value = "#{param.id}")
    private String id;

    private Keyword keyword;

    @Autowired
    private KeywordService keywordService;

    @PostConstruct
    private void init() {
        keyword = keywordService.get(id);
    }

}
