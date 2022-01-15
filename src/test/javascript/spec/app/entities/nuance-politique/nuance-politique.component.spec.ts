/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NuancePolitiqueComponent from '@/entities/nuance-politique/nuance-politique.vue';
import NuancePolitiqueClass from '@/entities/nuance-politique/nuance-politique.component';
import NuancePolitiqueService from '@/entities/nuance-politique/nuance-politique.service';
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
  describe('NuancePolitique Management Component', () => {
    let wrapper: Wrapper<NuancePolitiqueClass>;
    let comp: NuancePolitiqueClass;
    let nuancePolitiqueServiceStub: SinonStubbedInstance<NuancePolitiqueService>;

    beforeEach(() => {
      nuancePolitiqueServiceStub = sinon.createStubInstance<NuancePolitiqueService>(NuancePolitiqueService);
      nuancePolitiqueServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<NuancePolitiqueClass>(NuancePolitiqueComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          nuancePolitiqueService: () => nuancePolitiqueServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      nuancePolitiqueServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllNuancePolitiques();
      await comp.$nextTick();

      // THEN
      expect(nuancePolitiqueServiceStub.retrieve.called).toBeTruthy();
      expect(comp.nuancePolitiques[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      nuancePolitiqueServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(nuancePolitiqueServiceStub.retrieve.callCount).toEqual(1);

      comp.removeNuancePolitique();
      await comp.$nextTick();

      // THEN
      expect(nuancePolitiqueServiceStub.delete.called).toBeTruthy();
      expect(nuancePolitiqueServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
