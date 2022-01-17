/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NomsComponent from '@/entities/noms/noms.vue';
import NomsClass from '@/entities/noms/noms.component';
import NomsService from '@/entities/noms/noms.service';
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
  describe('Noms Management Component', () => {
    let wrapper: Wrapper<NomsClass>;
    let comp: NomsClass;
    let nomsServiceStub: SinonStubbedInstance<NomsService>;

    beforeEach(() => {
      nomsServiceStub = sinon.createStubInstance<NomsService>(NomsService);
      nomsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<NomsClass>(NomsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          nomsService: () => nomsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      nomsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllNomss();
      await comp.$nextTick();

      // THEN
      expect(nomsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.noms[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      nomsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(nomsServiceStub.retrieve.callCount).toEqual(1);

      comp.removeNoms();
      await comp.$nextTick();

      // THEN
      expect(nomsServiceStub.delete.called).toBeTruthy();
      expect(nomsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
