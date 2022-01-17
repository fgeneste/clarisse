import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import PersonneService from './personne/personne.service';
import NuancePolitiqueService from './nuance-politique/nuance-politique.service';
import NomsService from './noms/noms.service';
import AppartenanceService from './appartenance/appartenance.service';
import StructureService from './structure/structure.service';
import TypeStructureService from './type-structure/type-structure.service';
import TypeAppartenanceService from './type-appartenance/type-appartenance.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('personneService') private personneService = () => new PersonneService();
  @Provide('nuancePolitiqueService') private nuancePolitiqueService = () => new NuancePolitiqueService();
  @Provide('nomsService') private nomsService = () => new NomsService();
  @Provide('appartenanceService') private appartenanceService = () => new AppartenanceService();
  @Provide('structureService') private structureService = () => new StructureService();
  @Provide('typeStructureService') private typeStructureService = () => new TypeStructureService();
  @Provide('typeAppartenanceService') private typeAppartenanceService = () => new TypeAppartenanceService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
