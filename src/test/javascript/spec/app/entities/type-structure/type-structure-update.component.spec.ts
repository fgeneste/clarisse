/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TypeStructureUpdateComponent from '@/entities/type-structure/type-structure-update.vue';
import TypeStructureClass from '@/entities/type-structure/type-structure-update.component';
import TypeStructureService from '@/entities/type-structure/type-structure.service';

import StructureService from '@/entities/structure/structure.service';
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
  describe('TypeStructure Management Update Component', () => {
    let wrapper: Wrapper<TypeStructureClass>;
    let comp: TypeStructureClass;
    let typeStructureServiceStub: SinonStubbedInstance<TypeStructureService>;

    beforeEach(() => {
      typeStructureServiceStub = sinon.createStubInstance<TypeStructureService>(TypeStructureService);

      wrapper = shallowMount<TypeStructureClass>(TypeStructureUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          typeStructureService: () => typeStructureServiceStub,
          alertService: () => new AlertService(),

          structureService: () =>
            sinon.createStubInstance<StructureService>(StructureService, {
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
        comp.typeStructure = entity;
        typeStructureServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeStructureServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.typeStructure = entity;
        typeStructureServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeStructureServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTypeStructure = { id: 123 };
        typeStructureServiceStub.find.resolves(foundTypeStructure);
        typeStructureServiceStub.retrieve.resolves([foundTypeStructure]);

        // WHEN
        comp.beforeRouteEnter({ params: { typeStructureId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.typeStructure).toBe(foundTypeStructure);
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
