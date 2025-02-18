import {
  inject,
  provideAppInitializer
} from '@angular/core';
import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient} from '@angular/common/http';
import { KeycloakService } from './utils/keycloak/keycloak.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    provideAppInitializer((): Promise<void> => {
      const initFn: () => Promise<void> = ((key: KeycloakService): () => Promise<void> => {
        return (): Promise<void> => key.init();
      })(inject(KeycloakService));
      return initFn();
    })
    ]
};
