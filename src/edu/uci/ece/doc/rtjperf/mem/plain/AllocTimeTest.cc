#include <iostream>
#include <sched.h>
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>
#include <sys/mman.h>
#include <unistd.h>

#include <stdio.h>

#define CLOCK_PERIOD_NS 1.42531763203
#define CLOCK_PERIOD_US 0.00142531763203

unsigned char *buffer; 
size_t usedMemory = 0;
size_t sizeInBytes;

class DummyVTable {

public:
  long d0, d1, d2, d3, d4, d5, d6, d7, d8, d9;
};


void
initVTable(DummyVTable * vt) {
  
  vt->d0 = 0;
  vt->d1 = 0;
  vt->d2 = 0;
  vt->d2 = 0;
  vt->d3 = 0;
  vt->d4 = 0;
  vt->d5 = 0;
  vt->d6 = 0;
  vt->d7 = 0;
  vt->d8 = 0;
  vt->d9 = 0;
}

void init(size_t sizeInBytes) {
  
  size_t pageSize = getpagesize();
  size_t div = sizeInBytes / pageSize;
  
  // Round up the amount to an event number of pages.
  if ((div * pageSize) != sizeInBytes)
    sizeInBytes = (div + 1) * pageSize;
  
  void *tmp = malloc(sizeInBytes);

  // Let's lock the memory
  mlock(buffer, sizeInBytes);
  
  for (size_t i = 0; i < sizeInBytes; i += pageSize) {
    ((char* )tmp)[i] = 0;
  }
  
  usedMemory = 0;
  memset(tmp, 0, sizeInBytes);
}


void *
allocateBytes(size_t size) {
  
  // We need to add a pad that says if we need to invoke finalizer for
  // the object allocated in the given chunk of memory
  int offset = sizeof(long);
  
  if (usedMemory + size + 2 + 2*offset >= sizeInBytes) {
    return NULL;
  }
  
  // Write down the size of the next allocated object. We need to keep
  // track of the size because we want to be able to get a pointer to
  // the allocated object and invoke a finalizer on it.
  long *slotSize = (long *)(buffer + usedMemory);
  *slotSize = (long)size;
  usedMemory += offset;
  
  unsigned char *finalizable = (unsigned char *)(buffer + usedMemory);
  *finalizable = 0x00;

  usedMemory = usedMemory + 1;
  
  void * ptr = buffer + usedMemory;  
  usedMemory = usedMemory + size;
  return ptr;
}

int main(int argc, char *argv[]) {

  DummyVTable *vt = new DummyVTable();
  sched_param sp;
  sp.sched_priority = 50;
  int retVal = sched_setscheduler(getpid(), SCHED_FIFO, &sp);
  if (retVal != 0)
    perror("sched_setscheduler");
  
  size_t count;
  size_t chunchSize;

  sscanf(argv[1], "%d", &count);
  sscanf(argv[1], "%d", &chunchSize);

  init(chunchSize + 2*count);

  unsigned long long int data[count];
  unsigned long cpuid;
  unsigned long long int start;
  unsigned long long int stop;
  char * buf;
  for (int i = 0; i < count; ++i) {
    __asm__ volatile (".byte 0x0f, 0x31" : "=A" (start));
    //    new char[chunchSize];
    buf = (char *)allocateBytes(chunchSize);
    initVTable(vt);
    __asm__ volatile (".byte 0x0f, 0x31" : "=A" (stop));
    data[i] = stop -  start;
  }

  for (int i = 0; i < count; ++i)
    std::cout << (data[i] * CLOCK_PERIOD_US) << "\n";

  // We are not releasing the memory... But that is OK, we are exiting
  // anyhow.
  return 0;
}
