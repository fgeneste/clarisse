/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AppartenanceUpdateComponent from '@/entities/appartenance/appartenance-update.vue';
import AppartenanceClass from '@/entities/appartenance/appartenance-update.component';
import AppartenanceService from '@/entities/appartenance/appartenance.service';

import TypeAppartenanceService from '@/entities/type-appartenance/type-appartenance.service';

import StructureService from '@/entities/structure/structure.service';

import PersonneService from '@/entities/personne/personne.service';
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
  describe('Appartenance Management Update Component', () => {
    let wrapper: Wrapper<AppartenanceClass>;
    let comp: AppartenanceClass;
    let appartenanceServiceStub: SinonStubbedInstance<AppartenanceService>;

    beforeEach(() => {
      appartenanceServiceStub = sinon.createStubInstance<AppartenanceService>(AppartenanceService);

      wrapper = shallowMount<AppartenanceClass>(AppartenanceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          appartenanceService: () => appartenanceServiceStub,
          alertService: () => new AlertService(),

          typeAppartenanceService: () =>
            sinon.createStubInstance<TypeAppartenanceService>(TypeAppartenanceService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          structureService: () =>
            sinon.createStubInstance<StructureService>(StructureService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          personneService: () =>
            sinon.createStubInstance<PersonneService>(PersonneService, {
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
        comp.appartenance = entity;
        appartenanceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(appartenanceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.appartenance = entity;
        appartenanceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(appartenanceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAppartenance = { id: 123 };
        appartenanceServiceStub.find.resolves(foundAppartenance);
        appartenanceServiceStub.retrieve.resolves([foundAppartenance]);

        // WHEN
        comp.beforeRouteEnter({ params: { appartenanceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.appartenance).toBe(foundAppartenance);
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
