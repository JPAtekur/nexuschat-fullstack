import {
  inject,
  provideAppInitializer
} from '@angular/core';
import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import { KeycloakService } from './utils/keycloak/keycloak.service';
import { keycloakHttpInterceptor } from './utils/http/keycloak-http.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([keycloakHttpInterceptor])
    ),
    provideAppInitializer((): Promise<void> => {
      const initFn: () => Promise<void> = ((key: KeycloakService): () => Promise<void> => {
        return (): Promise<void> => key.init();
      })(inject(KeycloakService));
      return initFn();
    })
    ]
};
