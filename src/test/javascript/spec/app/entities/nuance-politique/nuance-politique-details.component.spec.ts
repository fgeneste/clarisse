/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import NuancePolitiqueDetailComponent from '@/entities/nuance-politique/nuance-politique-details.vue';
import NuancePolitiqueClass from '@/entities/nuance-politique/nuance-politique-details.component';
import NuancePolitiqueService from '@/entities/nuance-politique/nuance-politique.service';
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
  describe('NuancePolitique Management Detail Component', () => {
    let wrapper: Wrapper<NuancePolitiqueClass>;
    let comp: NuancePolitiqueClass;
    let nuancePolitiqueServiceStub: SinonStubbedInstance<NuancePolitiqueService>;

    beforeEach(() => {
      nuancePolitiqueServiceStub = sinon.createStubInstance<NuancePolitiqueService>(NuancePolitiqueService);

      wrapper = shallowMount<NuancePolitiqueClass>(NuancePolitiqueDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { nuancePolitiqueService: () => nuancePolitiqueServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundNuancePolitique = { id: 123 };
        nuancePolitiqueServiceStub.find.resolves(foundNuancePolitique);

        // WHEN
        comp.retrieveNuancePolitique(123);
        await comp.$nextTick();

        // THEN
        expect(comp.nuancePolitique).toBe(foundNuancePolitique);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNuancePolitique = { id: 123 };
        nuancePolitiqueServiceStub.find.resolves(foundNuancePolitique);

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
