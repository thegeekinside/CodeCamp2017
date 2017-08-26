import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { ProdConfig } from './blocks/config/prod.config';
import { CodeCamp2017AppModule } from './app.module';

ProdConfig();

if (module['hot']) {
    module['hot'].accept();
}

platformBrowserDynamic().bootstrapModule(CodeCamp2017AppModule)
.then((success) => console.log(`Application started`))
.catch((err) => console.error(err));
