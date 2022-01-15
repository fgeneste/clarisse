/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NomsUpdateComponent from '@/entities/noms/noms-update.vue';
import NomsClass from '@/entities/noms/noms-update.component';
import NomsService from '@/entities/noms/noms.service';

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
  describe('Noms Management Update Component', () => {
    let wrapper: Wrapper<NomsClass>;
    let comp: NomsClass;
    let nomsServiceStub: SinonStubbedInstance<NomsService>;

    beforeEach(() => {
      nomsServiceStub = sinon.createStubInstance<NomsService>(NomsService);

      wrapper = shallowMount<NomsClass>(NomsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          nomsService: () => nomsServiceStub,
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
        comp.noms = entity;
        nomsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nomsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.noms = entity;
        nomsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nomsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNoms = { id: 123 };
        nomsServiceStub.find.resolves(foundNoms);
        nomsServiceStub.retrieve.resolves([foundNoms]);

        // WHEN
        comp.beforeRouteEnter({ params: { nomsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.noms).toBe(foundNoms);
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
