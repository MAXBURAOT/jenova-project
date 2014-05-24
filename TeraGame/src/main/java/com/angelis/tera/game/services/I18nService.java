package com.angelis.tera.game.services;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class I18nService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(I18nService.class.getName());
    
    @Override
    public void onInit() {
        log.info("I18nService started");
    }

    @Override
    public void onDestroy() {
        log.info("I18nService stopped");
    }
    
    public String translate(final Locale locale, final String key, final Object... args) {
        String message = null;
        try {
            final ResourceBundle messages = ResourceBundle.getBundle("com.angelis.tera.game.i18n.messages", locale);
            message = messages.getString(key);
        } catch (final Exception e) {
            log.error("Can't find translation for key {"+key+"}");
        }
        
        return message;
    }

    /** SINGLETON */
    public static I18nService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final I18nService instance = new I18nService();
    }
}
