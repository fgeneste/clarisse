/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NuancePolitiqueUpdateComponent from '@/entities/nuance-politique/nuance-politique-update.vue';
import NuancePolitiqueClass from '@/entities/nuance-politique/nuance-politique-update.component';
import NuancePolitiqueService from '@/entities/nuance-politique/nuance-politique.service';

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
  describe('NuancePolitique Management Update Component', () => {
    let wrapper: Wrapper<NuancePolitiqueClass>;
    let comp: NuancePolitiqueClass;
    let nuancePolitiqueServiceStub: SinonStubbedInstance<NuancePolitiqueService>;

    beforeEach(() => {
      nuancePolitiqueServiceStub = sinon.createStubInstance<NuancePolitiqueService>(NuancePolitiqueService);

      wrapper = shallowMount<NuancePolitiqueClass>(NuancePolitiqueUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          nuancePolitiqueService: () => nuancePolitiqueServiceStub,
          alertService: () => new AlertService(),

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
        comp.nuancePolitique = entity;
        nuancePolitiqueServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nuancePolitiqueServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.nuancePolitique = entity;
        nuancePolitiqueServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nuancePolitiqueServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNuancePolitique = { id: 123 };
        nuancePolitiqueServiceStub.find.resolves(foundNuancePolitique);
        nuancePolitiqueServiceStub.retrieve.resolves([foundNuancePolitique]);

        // WHEN
        comp.beforeRouteEnter({ params: { nuancePolitiqueId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.nuancePolitique).toBe(foundNuancePolitique);
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
