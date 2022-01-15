import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import PersonneService from './personne/personne.service';
import NuancePolitiqueService from './nuance-politique/nuance-politique.service';
import NomsService from './noms/noms.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('personneService') private personneService = () => new PersonneService();
  @Provide('nuancePolitiqueService') private nuancePolitiqueService = () => new NuancePolitiqueService();
  @Provide('nomsService') private nomsService = () => new NomsService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
