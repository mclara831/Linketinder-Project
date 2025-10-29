type EventCallback = (...args: any[]) => void;

const events: Record<string, EventCallback[]> = {} as Record<string, EventCallback[]>;

export function subscribe(event :string, callback: EventCallback): void {
    if (!events[event])
        events[event] = [];

    events[event].push(callback)
}

export function notify(event :string): void {
    if (!events[event]) { return }
    events[event].forEach(cb => cb())
}