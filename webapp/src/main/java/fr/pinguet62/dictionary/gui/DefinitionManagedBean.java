package fr.pinguet62.dictionary.gui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

public final class DefinitionManagedBean {

    @ManagedProperty(value = "#{param.id}")
    private String id;

    // @Autowired
    // private KeywordService keywordService;

    @PostConstruct
    private void init() {
        // keyword = keywordService.get(id);
    }

}
