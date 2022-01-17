/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PersonneUpdateComponent from '@/entities/personne/personne-update.vue';
import PersonneClass from '@/entities/personne/personne-update.component';
import PersonneService from '@/entities/personne/personne.service';

import NuancePolitiqueService from '@/entities/nuance-politique/nuance-politique.service';

import NomsService from '@/entities/noms/noms.service';

import AppartenanceService from '@/entities/appartenance/appartenance.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Personne Management Update Component', () => {
    let wrapper: Wrapper<PersonneClass>;
    let comp: PersonneClass;
    let personneServiceStub: SinonStubbedInstance<PersonneService>;

    beforeEach(() => {
      personneServiceStub = sinon.createStubInstance<PersonneService>(PersonneService);

      wrapper = shallowMount<PersonneClass>(PersonneUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          personneService: () => personneServiceStub,
          alertService: () => new AlertService(),

          nuancePolitiqueService: () =>
            sinon.createStubInstance<NuancePolitiqueService>(NuancePolitiqueService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          nomsService: () =>
            sinon.createStubInstance<NomsService>(NomsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          appartenanceService: () =>
            sinon.createStubInstance<AppartenanceService>(AppartenanceService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.personne = entity;
        personneServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personneServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.personne = entity;
        personneServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personneServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPersonne = { id: 123 };
        personneServiceStub.find.resolves(foundPersonne);
        personneServiceStub.retrieve.resolves([foundPersonne]);

        // WHEN
        comp.beforeRouteEnter({ params: { personneId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.personne).toBe(foundPersonne);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
