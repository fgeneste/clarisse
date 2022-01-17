import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Personne = () => import('@/entities/personne/personne.vue');
// prettier-ignore
const PersonneUpdate = () => import('@/entities/personne/personne-update.vue');
// prettier-ignore
const PersonneDetails = () => import('@/entities/personne/personne-details.vue');
// prettier-ignore
const NuancePolitique = () => import('@/entities/nuance-politique/nuance-politique.vue');
// prettier-ignore
const NuancePolitiqueUpdate = () => import('@/entities/nuance-politique/nuance-politique-update.vue');
// prettier-ignore
const NuancePolitiqueDetails = () => import('@/entities/nuance-politique/nuance-politique-details.vue');
// prettier-ignore
const Noms = () => import('@/entities/noms/noms.vue');
// prettier-ignore
const NomsUpdate = () => import('@/entities/noms/noms-update.vue');
// prettier-ignore
const NomsDetails = () => import('@/entities/noms/noms-details.vue');
// prettier-ignore
const Appartenance = () => import('@/entities/appartenance/appartenance.vue');
// prettier-ignore
const AppartenanceUpdate = () => import('@/entities/appartenance/appartenance-update.vue');
// prettier-ignore
const AppartenanceDetails = () => import('@/entities/appartenance/appartenance-details.vue');
// prettier-ignore
const Structure = () => import('@/entities/structure/structure.vue');
// prettier-ignore
const StructureUpdate = () => import('@/entities/structure/structure-update.vue');
// prettier-ignore
const StructureDetails = () => import('@/entities/structure/structure-details.vue');
// prettier-ignore
const TypeStructure = () => import('@/entities/type-structure/type-structure.vue');
// prettier-ignore
const TypeStructureUpdate = () => import('@/entities/type-structure/type-structure-update.vue');
// prettier-ignore
const TypeStructureDetails = () => import('@/entities/type-structure/type-structure-details.vue');
// prettier-ignore
const TypeAppartenance = () => import('@/entities/type-appartenance/type-appartenance.vue');
// prettier-ignore
const TypeAppartenanceUpdate = () => import('@/entities/type-appartenance/type-appartenance-update.vue');
// prettier-ignore
const TypeAppartenanceDetails = () => import('@/entities/type-appartenance/type-appartenance-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'personne',
      name: 'Personne',
      component: Personne,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'personne/new',
      name: 'PersonneCreate',
      component: PersonneUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'personne/:personneId/edit',
      name: 'PersonneEdit',
      component: PersonneUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'personne/:personneId/view',
      name: 'PersonneView',
      component: PersonneDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nuance-politique',
      name: 'NuancePolitique',
      component: NuancePolitique,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nuance-politique/new',
      name: 'NuancePolitiqueCreate',
      component: NuancePolitiqueUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nuance-politique/:nuancePolitiqueId/edit',
      name: 'NuancePolitiqueEdit',
      component: NuancePolitiqueUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nuance-politique/:nuancePolitiqueId/view',
      name: 'NuancePolitiqueView',
      component: NuancePolitiqueDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'noms',
      name: 'Noms',
      component: Noms,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'noms/new',
      name: 'NomsCreate',
      component: NomsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'noms/:nomsId/edit',
      name: 'NomsEdit',
      component: NomsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'noms/:nomsId/view',
      name: 'NomsView',
      component: NomsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appartenance',
      name: 'Appartenance',
      component: Appartenance,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appartenance/new',
      name: 'AppartenanceCreate',
      component: AppartenanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appartenance/:appartenanceId/edit',
      name: 'AppartenanceEdit',
      component: AppartenanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appartenance/:appartenanceId/view',
      name: 'AppartenanceView',
      component: AppartenanceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'structure',
      name: 'Structure',
      component: Structure,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'structure/new',
      name: 'StructureCreate',
      component: StructureUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'structure/:structureId/edit',
      name: 'StructureEdit',
      component: StructureUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'structure/:structureId/view',
      name: 'StructureView',
      component: StructureDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-structure',
      name: 'TypeStructure',
      component: TypeStructure,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-structure/new',
      name: 'TypeStructureCreate',
      component: TypeStructureUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-structure/:typeStructureId/edit',
      name: 'TypeStructureEdit',
      component: TypeStructureUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-structure/:typeStructureId/view',
      name: 'TypeStructureView',
      component: TypeStructureDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-appartenance',
      name: 'TypeAppartenance',
      component: TypeAppartenance,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-appartenance/new',
      name: 'TypeAppartenanceCreate',
      component: TypeAppartenanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-appartenance/:typeAppartenanceId/edit',
      name: 'TypeAppartenanceEdit',
      component: TypeAppartenanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-appartenance/:typeAppartenanceId/view',
      name: 'TypeAppartenanceView',
      component: TypeAppartenanceDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
