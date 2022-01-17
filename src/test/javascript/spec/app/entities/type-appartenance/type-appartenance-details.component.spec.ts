/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TypeAppartenanceDetailComponent from '@/entities/type-appartenance/type-appartenance-details.vue';
import TypeAppartenanceClass from '@/entities/type-appartenance/type-appartenance-details.component';
import TypeAppartenanceService from '@/entities/type-appartenance/type-appartenance.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TypeAppartenance Management Detail Component', () => {
    let wrapper: Wrapper<TypeAppartenanceClass>;
    let comp: TypeAppartenanceClass;
    let typeAppartenanceServiceStub: SinonStubbedInstance<TypeAppartenanceService>;

    beforeEach(() => {
      typeAppartenanceServiceStub = sinon.createStubInstance<TypeAppartenanceService>(TypeAppartenanceService);

      wrapper = shallowMount<TypeAppartenanceClass>(TypeAppartenanceDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { typeAppartenanceService: () => typeAppartenanceServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTypeAppartenance = { id: 123 };
        typeAppartenanceServiceStub.find.resolves(foundTypeAppartenance);

        // WHEN
        comp.retrieveTypeAppartenance(123);
        await comp.$nextTick();

        // THEN
        expect(comp.typeAppartenance).toBe(foundTypeAppartenance);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTypeAppartenance = { id: 123 };
        typeAppartenanceServiceStub.find.resolves(foundTypeAppartenance);

        // WHEN
        comp.beforeRouteEnter({ params: { typeAppartenanceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.typeAppartenance).toBe(foundTypeAppartenance);
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
