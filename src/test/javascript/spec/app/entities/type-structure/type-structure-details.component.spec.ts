/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TypeStructureDetailComponent from '@/entities/type-structure/type-structure-details.vue';
import TypeStructureClass from '@/entities/type-structure/type-structure-details.component';
import TypeStructureService from '@/entities/type-structure/type-structure.service';
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
  describe('TypeStructure Management Detail Component', () => {
    let wrapper: Wrapper<TypeStructureClass>;
    let comp: TypeStructureClass;
    let typeStructureServiceStub: SinonStubbedInstance<TypeStructureService>;

    beforeEach(() => {
      typeStructureServiceStub = sinon.createStubInstance<TypeStructureService>(TypeStructureService);

      wrapper = shallowMount<TypeStructureClass>(TypeStructureDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { typeStructureService: () => typeStructureServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTypeStructure = { id: 123 };
        typeStructureServiceStub.find.resolves(foundTypeStructure);

        // WHEN
        comp.retrieveTypeStructure(123);
        await comp.$nextTick();

        // THEN
        expect(comp.typeStructure).toBe(foundTypeStructure);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTypeStructure = { id: 123 };
        typeStructureServiceStub.find.resolves(foundTypeStructure);

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
