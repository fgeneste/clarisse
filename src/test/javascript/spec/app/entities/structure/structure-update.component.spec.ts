/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StructureUpdateComponent from '@/entities/structure/structure-update.vue';
import StructureClass from '@/entities/structure/structure-update.component';
import StructureService from '@/entities/structure/structure.service';

import TypeStructureService from '@/entities/type-structure/type-structure.service';

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
  describe('Structure Management Update Component', () => {
    let wrapper: Wrapper<StructureClass>;
    let comp: StructureClass;
    let structureServiceStub: SinonStubbedInstance<StructureService>;

    beforeEach(() => {
      structureServiceStub = sinon.createStubInstance<StructureService>(StructureService);

      wrapper = shallowMount<StructureClass>(StructureUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          structureService: () => structureServiceStub,
          alertService: () => new AlertService(),

          typeStructureService: () =>
            sinon.createStubInstance<TypeStructureService>(TypeStructureService, {
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
        comp.structure = entity;
        structureServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(structureServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.structure = entity;
        structureServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(structureServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStructure = { id: 123 };
        structureServiceStub.find.resolves(foundStructure);
        structureServiceStub.retrieve.resolves([foundStructure]);

        // WHEN
        comp.beforeRouteEnter({ params: { structureId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.structure).toBe(foundStructure);
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
