package itcom.gangstersquirrel.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import itcom.gangstersquirrel.MainGameClass;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(MainGameClass.WIDTH, MainGameClass.HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new MainGameClass();
        }
}