/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AppartenanceDetailComponent from '@/entities/appartenance/appartenance-details.vue';
import AppartenanceClass from '@/entities/appartenance/appartenance-details.component';
import AppartenanceService from '@/entities/appartenance/appartenance.service';
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
  describe('Appartenance Management Detail Component', () => {
    let wrapper: Wrapper<AppartenanceClass>;
    let comp: AppartenanceClass;
    let appartenanceServiceStub: SinonStubbedInstance<AppartenanceService>;

    beforeEach(() => {
      appartenanceServiceStub = sinon.createStubInstance<AppartenanceService>(AppartenanceService);

      wrapper = shallowMount<AppartenanceClass>(AppartenanceDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { appartenanceService: () => appartenanceServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAppartenance = { id: 123 };
        appartenanceServiceStub.find.resolves(foundAppartenance);

        // WHEN
        comp.retrieveAppartenance(123);
        await comp.$nextTick();

        // THEN
        expect(comp.appartenance).toBe(foundAppartenance);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAppartenance = { id: 123 };
        appartenanceServiceStub.find.resolves(foundAppartenance);

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
