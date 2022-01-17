/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StructureComponent from '@/entities/structure/structure.vue';
import StructureClass from '@/entities/structure/structure.component';
import StructureService from '@/entities/structure/structure.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Structure Management Component', () => {
    let wrapper: Wrapper<StructureClass>;
    let comp: StructureClass;
    let structureServiceStub: SinonStubbedInstance<StructureService>;

    beforeEach(() => {
      structureServiceStub = sinon.createStubInstance<StructureService>(StructureService);
      structureServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StructureClass>(StructureComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          structureService: () => structureServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      structureServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStructures();
      await comp.$nextTick();

      // THEN
      expect(structureServiceStub.retrieve.called).toBeTruthy();
      expect(comp.structures[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      structureServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(structureServiceStub.retrieve.callCount).toEqual(1);

      comp.removeStructure();
      await comp.$nextTick();

      // THEN
      expect(structureServiceStub.delete.called).toBeTruthy();
      expect(structureServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
