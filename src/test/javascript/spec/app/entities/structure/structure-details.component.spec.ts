/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import StructureDetailComponent from '@/entities/structure/structure-details.vue';
import StructureClass from '@/entities/structure/structure-details.component';
import StructureService from '@/entities/structure/structure.service';
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
  describe('Structure Management Detail Component', () => {
    let wrapper: Wrapper<StructureClass>;
    let comp: StructureClass;
    let structureServiceStub: SinonStubbedInstance<StructureService>;

    beforeEach(() => {
      structureServiceStub = sinon.createStubInstance<StructureService>(StructureService);

      wrapper = shallowMount<StructureClass>(StructureDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { structureService: () => structureServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStructure = { id: 123 };
        structureServiceStub.find.resolves(foundStructure);

        // WHEN
        comp.retrieveStructure(123);
        await comp.$nextTick();

        // THEN
        expect(comp.structure).toBe(foundStructure);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStructure = { id: 123 };
        structureServiceStub.find.resolves(foundStructure);

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
