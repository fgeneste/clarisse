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
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
