/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TypeStructureComponent from '@/entities/type-structure/type-structure.vue';
import TypeStructureClass from '@/entities/type-structure/type-structure.component';
import TypeStructureService from '@/entities/type-structure/type-structure.service';
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
  describe('TypeStructure Management Component', () => {
    let wrapper: Wrapper<TypeStructureClass>;
    let comp: TypeStructureClass;
    let typeStructureServiceStub: SinonStubbedInstance<TypeStructureService>;

    beforeEach(() => {
      typeStructureServiceStub = sinon.createStubInstance<TypeStructureService>(TypeStructureService);
      typeStructureServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TypeStructureClass>(TypeStructureComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          typeStructureService: () => typeStructureServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      typeStructureServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTypeStructures();
      await comp.$nextTick();

      // THEN
      expect(typeStructureServiceStub.retrieve.called).toBeTruthy();
      expect(comp.typeStructures[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      typeStructureServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(typeStructureServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTypeStructure();
      await comp.$nextTick();

      // THEN
      expect(typeStructureServiceStub.delete.called).toBeTruthy();
      expect(typeStructureServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
